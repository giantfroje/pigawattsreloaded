package dev.giantfroje.pigawattsreloaded.block.entity;

import dev.giantfroje.pigawattsreloaded.PigawattsReloaded;
import dev.giantfroje.pigawattsreloaded.block.TreadmillBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import team.reborn.energy.EnergySide;
import team.reborn.energy.EnergyStorage;
import team.reborn.energy.EnergyTier;

public class TreadmillBlockEntity extends BlockEntity implements Tickable, EnergyStorage, IAnimatable {
    public TreadmillBlockEntity() {
        super(PigawattsReloaded.TREADMILL_BLOCK_ENTITY);
    }

    private final AnimationFactory factory = new AnimationFactory(this);

    private double energy;

    private BlockPos otherBlockPos = pos;

    @Override
    public void tick() {
        if (world != null) {
            BlockState state = world.getBlockState(pos);
            if (!state.get(TreadmillBlock.FRONT)) {
                if (state.get(TreadmillBlock.ON)) {
                    setStored(getStored(EnergySide.UNKNOWN) + 2);
                }
                world.setBlockState(pos, state.with(TreadmillBlock.GENERATING, (getStored(EnergySide.UNKNOWN) < getMaxStoredPower() && state.get(TreadmillBlock.ON))));
                if (world.getBlockState(otherBlockPos).getBlock() == PigawattsReloaded.TREADMILL_BLOCK) {
                    world.setBlockState(otherBlockPos, world.getBlockState(otherBlockPos).with(TreadmillBlock.GENERATING, (getStored(EnergySide.UNKNOWN) < getMaxStoredPower() && state.get(TreadmillBlock.ON))));
                }
                markDirty();
            }
        }
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        energy = tag.getDouble("energy");

        CompoundTag otherBlockPosTag = tag.getCompound("otherBlockPos");
        otherBlockPos = new BlockPos(otherBlockPosTag.getInt("x"), otherBlockPosTag.getInt("y"), otherBlockPosTag.getInt("z"));
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putDouble("energy", energy);

        CompoundTag otherBlockPosTag = new CompoundTag();
        otherBlockPosTag.putInt("x", otherBlockPos.getX());
        otherBlockPosTag.putInt("y", otherBlockPos.getY());
        otherBlockPosTag.putInt("z", otherBlockPos.getZ());
        tag.put("otherBlockPos", otherBlockPosTag);

        return super.toTag(tag);
    }

    private <E extends BlockEntity & IAnimatable> PlayState generate(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 20;
        event.getController().setAnimation(new AnimationBuilder().addAnimation("pig.run", true));
        if (world != null) {
            if (world.getBlockState(pos).get(TreadmillBlock.GENERATING)) {
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::generate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public double getStored(EnergySide face) {
        if (world != null) {
            if (world.getBlockState(pos).get(TreadmillBlock.FRONT)) {
                TreadmillBlockEntity otherBlockEntity = (TreadmillBlockEntity) world.getBlockEntity(otherBlockPos);
                if (otherBlockEntity != null) {
                    return otherBlockEntity.energy;
                }
            }
        }
        return energy;
    }

    @Override
    public void setStored(double amount) {
        energy = Math.max(Math.min(amount, getMaxStoredPower()), 0);
        markDirty();
    }

    @Override
    public double getMaxStoredPower() {
        return 5000;
    }

    @Override
    public EnergyTier getTier() {
        return EnergyTier.MEDIUM;
    }

    public void setOtherBlockPos(BlockPos blockPos) {
        otherBlockPos = blockPos;
    }
}
