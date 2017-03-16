package eyeq.homo.entity.passive;

import com.google.common.base.Predicate;
import eyeq.util.client.MinecraftUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class AbstractEntityHomo extends EntityAnimal {
    public static final Predicate<EntityPlayer> STEVE_SELECTOR = player -> "default".equals(MinecraftUtils.getSkinType(player));
    public static final Predicate<EntityPlayer> ALEX_SELECTOR = player -> "slim".equals(MinecraftUtils.getSkinType(player));

    public AbstractEntityHomo(World world) {
        super(world);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(motionX * motionX + motionZ * motionZ > 2.5E-007D && rand.nextInt(5) == 0) {
            int i = MathHelper.floor(posX);
            int j = MathHelper.floor(posY - 0.2 - this.getYOffset());
            int k = MathHelper.floor(posZ);
            IBlockState state = world.getBlockState(new BlockPos(i, j, k));
            if(state.getRenderType() != EnumBlockRenderType.INVISIBLE) {
                world.spawnParticle(EnumParticleTypes.BLOCK_CRACK,
                        posX + (rand.nextFloat() - 0.5) * width,
                        posY + 0.1,
                        posZ + (rand.nextFloat() - 0.5) * width,
                        4 * (rand.nextFloat() - 0.5),
                        0.5, (rand.nextFloat() - 0.5) * 4,
                        Block.getStateId(state));
            }
        }
        double dx = this.rand.nextGaussian() * 0.02;
        double dy = this.rand.nextGaussian() * 0.02;
        double dz = this.rand.nextGaussian() * 0.02;
        this.world.spawnParticle(EnumParticleTypes.HEART,
                this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                this.posY + 0.5 + this.rand.nextFloat() * this.height,
                this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                dx, dy, dz);

        if(this.world.isRemote) {
            return;
        }
        EntityLivingBase target = this.getAttackTarget();
        if(target == null || !onGround) {
            return;
        }
        if(target instanceof EntityPlayer) {
            if(((EntityPlayer) target).inventory.hasItemStack(new ItemStack(Items.BOOK))) {
                this.motionX *= 1.525F;
                this.motionZ *= 1.525F;
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if(entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2 + rand.nextInt(2))) {
            entity.motionY += 0.4;
            return true;
        }
        return false;
    }

    @Override
    public void setInWeb() {
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    protected Item getDropItem() {
        return Items.BOOK;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }
}
