package eyeq.homo.entity.passive;

import eyeq.homo.Homo;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityHomo extends AbstractEntityHomo {
    public EntityHomo(World world) {
        super(world);
        this.setSize(1.5F, 1.4F);
        this.setHealth(16F);
        ((PathNavigateGround) this.getNavigator()).setCanSwim(true);
        this.isImmuneToFire = true;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 0.42, false));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35));
        this.tasks.addTask(5, new EntityAIWander(this, 0.35));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, false, AbstractEntityHomo.STEVE_SELECTOR));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return Homo.entityHomoAmbient;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if(entity == Minecraft.getMinecraft().player) {
            ITextComponent text = new TextComponentTranslation(Homo.I18n_HOMO);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(text);
        }
        return super.attackEntityAsMob(entity);
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.world.countEntities(EntityHomo.class) <= 30;
    }
}
