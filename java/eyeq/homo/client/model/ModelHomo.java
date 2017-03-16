package eyeq.homo.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelHomo extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer armRight;
    public ModelRenderer armLeft;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;

    public ModelHomo() {
        body = new ModelRenderer(this, 0, 0);
        body.addBox(-5F, -5F, -8F, 10, 10, 16);
        body.setRotationPoint(0F, 16F, 0F);

        armRight = new ModelRenderer(this, 0, 0);
        armRight.addBox(-4F, -1F, -1F, 4, 2, 2);
        armRight.addBox(-4F, -1F, -1F, 2, 6, 2);
        armRight.setRotationPoint(-5F, 16F, -4F);
        armLeft = new ModelRenderer(this, 0, 0);
        armLeft.addBox(0F, -1F, -1F, 4, 2, 2);
        armLeft.addBox(2F, -1F, -1F, 2, 6, 2);
        armLeft.setRotationPoint(5F, 16F, -4F);

        legRight = new ModelRenderer(this, 0, 0);
        legRight.addBox(-4F, -1F, -1F, 4, 2, 2);
        legRight.addBox(-4F, -1F, -1F, 2, 6, 2);
        legRight.setRotationPoint(-5F, 16F, 4F);
        legLeft = new ModelRenderer(this, 0, 0);
        legLeft.addBox(0F, -1F, -1F, 4, 2, 2);
        legLeft.addBox(2F, -1F, -1F, 2, 6, 2);
        legLeft.setRotationPoint(5F, 16F, 4F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        body.render(scale);
        armRight.render(scale);
        armLeft.render(scale);
        legRight.render(scale);
        legLeft.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        limbSwing *= 0.6662F;
        ageInTicks *= 0.6662F;
        float rotate;
        rotate = MathHelper.cos(limbSwing) * 1.4F * limbSwingAmount;
        armRight.rotateAngleX = rotate;
        legLeft.rotateAngleX = rotate;

        rotate = MathHelper.cos(limbSwing + (float) Math.PI) * 1.4F * limbSwingAmount;
        armLeft.rotateAngleX = rotate;
        legRight.rotateAngleX = rotate;

        rotate = MathHelper.cos(ageInTicks + 0.015F * (float) Math.PI) * (float) Math.PI * 0.1F;
        armRight.rotateAngleY = rotate;
        armLeft.rotateAngleY = -rotate;
        legRight.rotateAngleY = rotate;
        legLeft.rotateAngleY = -rotate;

        limbSwing *= 0.5F;
        rotate = MathHelper.sin(limbSwing + (float) Math.PI) * 0.62F * limbSwingAmount;
        armRight.rotateAngleZ = rotate;
        legRight.rotateAngleZ = -rotate;

        rotate = MathHelper.sin(limbSwing) * 0.62F * limbSwingAmount;
        armLeft.rotateAngleZ = -rotate;
        legLeft.rotateAngleZ = rotate;
    }
}
