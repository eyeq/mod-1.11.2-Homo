package eyeq.homo.client.renderer.entity;

import eyeq.homo.client.model.ModelHomo;
import eyeq.util.client.renderer.EntityRenderResourceLocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import static eyeq.homo.Homo.MOD_ID;

public class RenderLes extends RenderLiving {
    protected static final ResourceLocation textures = new EntityRenderResourceLocation(MOD_ID, "les");

    private static float scale = 1.6F;

    public RenderLes(RenderManager renderManager) {
        super(renderManager, new ModelHomo(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return textures;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float partialTickTime) {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }
}
