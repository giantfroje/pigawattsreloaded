package dev.giantfroje.pigawattsreloaded.block;

import dev.giantfroje.pigawattsreloaded.PigawattsReloaded;
import dev.giantfroje.pigawattsreloaded.block.entity.TreadmillBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class TreadmillBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public TreadmillBlock(Settings settings) {
        super(settings.nonOpaque());
        setDefaultState(getStateManager().getDefaultState().with(FRONT, false).with(ON, false).with(FACING, Direction.NORTH));
    }

    public static final BooleanProperty FRONT = BooleanProperty.of("front");
    public static final BooleanProperty ON = BooleanProperty.of("on");
    public static final BooleanProperty GENERATING = BooleanProperty.of("generating");

    protected static final VoxelShape NORTH_SHAPE; // = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 0.1875f, 2.0f);
    protected static final VoxelShape SOUTH_SHAPE; // = VoxelShapes.cuboid(0.0f, 0.0f, -1.0f, 1.0f, 0.1875f, 1.0f);
    protected static final VoxelShape EAST_SHAPE; // = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 2.0f, 0.1875f, 1.0f);
    protected static final VoxelShape WEST_SHAPE; // = VoxelShapes.cuboid(-1.0f, 0.0f, 0.0f, 1.0f, 0.1875f, 1.0f);

    protected static final VoxelShape NORTH_SHAPE_FRONT; // = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 0.1875f, 2.0f);
    protected static final VoxelShape SOUTH_SHAPE_FRONT; // = VoxelShapes.cuboid(0.0f, 0.0f, -1.0f, 1.0f, 0.1875f, 1.0f);
    protected static final VoxelShape EAST_SHAPE_FRONT; // = VoxelShapes.cuboid(-1.0f, 0.0f, 0.0f, 1.0f, 0.1875f, 1.0f);
    protected static final VoxelShape WEST_SHAPE_FRONT; // = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 2.0f, 0.1875f, 1.0f);

    static {
        VoxelShape NORTH_SHAPE_1 = VoxelShapes.cuboid(0.0f, 0.0f, -0.5f, 1.0f, 0.1875f, 1.0f);
        VoxelShape NORTH_SHAPE_2 = VoxelShapes.cuboid(0.0f, 0.0f, -1.0f, 1.0f, 0.25f, -0.5f);
        NORTH_SHAPE = VoxelShapes.union(NORTH_SHAPE_1, NORTH_SHAPE_2);
        VoxelShape SOUTH_SHAPE_1 = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 0.1875f, 1.5f);
        VoxelShape SOUTH_SHAPE_2 = VoxelShapes.cuboid(0.0f, 0.0f, 1.5f, 1.0f, 0.25f, 2f);
        SOUTH_SHAPE = VoxelShapes.union(SOUTH_SHAPE_1, SOUTH_SHAPE_2);
        VoxelShape EAST_SHAPE_1 = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.5f, 0.1875f, 1.0f);
        VoxelShape EAST_SHAPE_2 = VoxelShapes.cuboid(1.5f, 0.0f, 0.0f, 2.0f, 0.25f, 1.0f);
        EAST_SHAPE = VoxelShapes.union(EAST_SHAPE_1, EAST_SHAPE_2);
        VoxelShape WEST_SHAPE_1 = VoxelShapes.cuboid(-0.5f, 0.0f, 0.0f, 1.0f, 0.1875f, 1.0f);
        VoxelShape WEST_SHAPE_2 = VoxelShapes.cuboid(-0.5f, 0.0f, 0.0f, -1.0f, 0.25f, 1.0f);
        WEST_SHAPE = VoxelShapes.union(WEST_SHAPE_1, WEST_SHAPE_2);

        VoxelShape NORTH_SHAPE_FRONT_1 = VoxelShapes.cuboid(0.0f, 0.0f, 0.5f, 1.0f, 0.1875f, 2.0f);
        VoxelShape NORTH_SHAPE_FRONT_2 = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 0.5f);
        NORTH_SHAPE_FRONT = VoxelShapes.union(NORTH_SHAPE_FRONT_1, NORTH_SHAPE_FRONT_2);
        VoxelShape SOUTH_SHAPE_FRONT_1 = VoxelShapes.cuboid(0.0f, 0.0f, -1.0f, 1.0f, 0.1875f, 0.5f);
        VoxelShape SOUTH_SHAPE_FRONT_2 = VoxelShapes.cuboid(0.0f, 0.0f, 0.5f, 1.0f, 0.25f, 1.0f);
        SOUTH_SHAPE_FRONT = VoxelShapes.union(SOUTH_SHAPE_FRONT_1, SOUTH_SHAPE_FRONT_2);
        VoxelShape EAST_SHAPE_FRONT_1 = VoxelShapes.cuboid(-1.0f, 0.0f, 0.0f, 0.5f, 0.1875f, 1.0f);
        VoxelShape EAST_SHAPE_FRONT_2 = VoxelShapes.cuboid(0.5f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
        EAST_SHAPE_FRONT = VoxelShapes.union(EAST_SHAPE_FRONT_1, EAST_SHAPE_FRONT_2);
        VoxelShape WEST_SHAPE_FRONT_1 = VoxelShapes.cuboid(0.5f, 0.0f, 0.0f, 2.0f, 0.1875f, 1.0f);
        VoxelShape WEST_SHAPE_FRONT_2 = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 0.5f, 0.25f, 1.0f);
        WEST_SHAPE_FRONT = VoxelShapes.union(WEST_SHAPE_FRONT_1, WEST_SHAPE_FRONT_2);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FRONT);
        stateManager.add(ON);
        stateManager.add(GENERATING);
        stateManager.add(FACING);
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, context.getPlayerFacing());
    }

    public VoxelShape getVoxelShape(BlockState state) {
        if (state.get(FRONT)) {
            switch(state.get(FACING)) {
                default:
                    return VoxelShapes.empty();
                case NORTH:
                    return NORTH_SHAPE_FRONT;
                case SOUTH:
                    return SOUTH_SHAPE_FRONT;
                case EAST:
                    return EAST_SHAPE_FRONT;
                case WEST:
                    return WEST_SHAPE_FRONT;
            }
        }
        switch(state.get(FACING)) {
            default:
                return VoxelShapes.empty();
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getVoxelShape(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getVoxelShape(state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        if (blockState.get(FRONT)) {
            return BlockRenderType.INVISIBLE;
        }
        return BlockRenderType.MODEL;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer != null) {
            world.setBlockState(pos.offset(placer.getHorizontalFacing(), 1), PigawattsReloaded.TREADMILL_BLOCK.getDefaultState().with(FRONT, true).with(FACING, state.get(FACING)));

            ((TreadmillBlockEntity) world.getBlockEntity(pos)).setOtherBlockPos(pos.offset(placer.getHorizontalFacing(), 1));
            ((TreadmillBlockEntity) world.getBlockEntity(pos.offset(placer.getHorizontalFacing(), 1))).setOtherBlockPos(pos.offset(placer.getHorizontalFacing(), 1));

            super.onPlaced(world, pos, state, placer, itemStack);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(ON)) {
            spawnPig(state, world, pos, 0);
        }
        if (state.get(FRONT)) {
            world.setBlockState(pos.offset(state.get(FACING).getOpposite(), 1), Blocks.AIR.getDefaultState());
        } else {
            world.setBlockState(pos.offset(state.get(FACING), 1), Blocks.AIR.getDefaultState());
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(ON)) {
            if (player.isSneaking()) {
                spawnPig(state, world, pos, 1);
                toggle(state, world, pos);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!state.get(ON)) {
            if (entity.getType() == EntityType.PIG) {
                TreadmillBlockEntity blockEntity = (TreadmillBlockEntity) world.getBlockEntity(pos);
                if (blockEntity != null) {
                    toggle(state, world, pos);
                    entity.remove();
                }
            }
            super.onEntityCollision(state, world, pos, entity);
            return;
        }
        Direction facing = state.get(FACING);
        if (facing == Direction.NORTH) {
            entity.addVelocity(0, 0, 1);
        } else if (facing == Direction.SOUTH) {
            entity.addVelocity(0, 0, -1);
        } else if (facing == Direction.EAST) {
            entity.addVelocity(-1, 0, 0);
        } else if (facing == Direction.WEST) {
            entity.addVelocity(1, 0, 0);
        }
        super.onEntityCollision(state, world, pos, entity);
    }
    
    public void toggle(BlockState state, World world, BlockPos pos) {
        BlockPos otherPos;
        if (!state.get(FRONT)) {
            otherPos = pos.offset(state.get(FACING), 1);
        } else {
            otherPos = pos.offset(state.get(FACING).getOpposite(), 1);
        }

        world.setBlockState(pos, state.with(ON, !state.get(ON)));
        world.setBlockState(otherPos, world.getBlockState(otherPos).with(TreadmillBlock.ON, !state.get(ON)));
    }

    public void spawnPig(BlockState state, World world, BlockPos pos, int offset) {
        Entity pig = EntityType.PIG.create(world);
        if (pig != null) {
            BlockPos pigPos;
            if (state.get(FRONT)) {
                pigPos = pos.offset(state.get(FACING).getOpposite(), offset + 1);
            } else {
                pigPos = pos.offset(state.get(FACING).getOpposite(), offset);
            }
            pig.refreshPositionAndAngles(pigPos, 0, 0);
            world.spawnEntity(pig);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TreadmillBlockEntity();
    }
}
