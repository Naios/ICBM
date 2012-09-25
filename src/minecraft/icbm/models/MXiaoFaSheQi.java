package icbm.models;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class MXiaoFaSheQi extends ModelBase
{
	  //fields
	    ModelRenderer SUPPORT_1;
	    ModelRenderer SUPPORT_2;
	    ModelRenderer SUPPORT_3;
	    ModelRenderer SUPPORT_4;
	    ModelRenderer SUPPORT_5;
	    ModelRenderer SUPPORT_6;
	    ModelRenderer MAIN_BODY;
	    ModelRenderer MAIN_SUPPORT;
	    ModelRenderer TORQUE_ROT;
	    ModelRenderer CLAMP_1_ROT;
	    ModelRenderer CLAMP_2_ROT;
	    ModelRenderer COUNTERBALANCE_1_ROT;
	    ModelRenderer COUNTERBALANCE_2_ROT;
	    ModelRenderer RAIL_1_ROT;
	    ModelRenderer RAIL_2_ROT;
	    ModelRenderer TORQUE_SUPPORT_ROT;
	  
	  public MXiaoFaSheQi()
	  {
	    textureWidth = 128;
	    textureHeight = 128;
	    
	      SUPPORT_1 = new ModelRenderer(this, 0, 0);
	      SUPPORT_1.addBox(-1F, 0F, -21F, 2, 1, 42);
	      SUPPORT_1.setRotationPoint(0F, 21F, 0F);
	      SUPPORT_1.setTextureSize(128, 128);
	      SUPPORT_1.mirror = true;
	      setRotation(SUPPORT_1, 0F, 0.7853982F, 0F);
	      SUPPORT_2 = new ModelRenderer(this, 0, 0);
	      SUPPORT_2.addBox(-1F, 0F, -21F, 2, 1, 42);
	      SUPPORT_2.setRotationPoint(0F, 21F, 0F);
	      SUPPORT_2.setTextureSize(128, 128);
	      SUPPORT_2.mirror = true;
	      setRotation(SUPPORT_2, 0F, 2.356194F, 0F);
	      SUPPORT_3 = new ModelRenderer(this, 0, 28);
	      SUPPORT_3.addBox(0F, 0F, 0F, 4, 3, 4);
	      SUPPORT_3.setRotationPoint(-16F, 21F, -16F);
	      SUPPORT_3.setTextureSize(128, 128);
	      SUPPORT_3.mirror = true;
	      setRotation(SUPPORT_3, 0F, 0F, 0F);
	      SUPPORT_4 = new ModelRenderer(this, 0, 28);
	      SUPPORT_4.addBox(0F, 0F, 0F, 4, 3, 4);
	      SUPPORT_4.setRotationPoint(12F, 21F, -16F);
	      SUPPORT_4.setTextureSize(128, 128);
	      SUPPORT_4.mirror = true;
	      setRotation(SUPPORT_4, 0F, 0F, 0F);
	      SUPPORT_5 = new ModelRenderer(this, 0, 28);
	      SUPPORT_5.addBox(0F, 0F, 0F, 4, 3, 4);
	      SUPPORT_5.setRotationPoint(12F, 21F, 12F);
	      SUPPORT_5.setTextureSize(128, 128);
	      SUPPORT_5.mirror = true;
	      setRotation(SUPPORT_5, 0F, 0F, 0F);
	      SUPPORT_6 = new ModelRenderer(this, 0, 28);
	      SUPPORT_6.addBox(0F, 0F, 0F, 4, 3, 4);
	      SUPPORT_6.setRotationPoint(-16F, 21F, 12F);
	      SUPPORT_6.setTextureSize(128, 128);
	      SUPPORT_6.mirror = true;
	      setRotation(SUPPORT_6, 0F, 0F, 0F);
	      MAIN_BODY = new ModelRenderer(this, 0, 46);
	      MAIN_BODY.addBox(0F, 0F, 0F, 6, 4, 6);
	      MAIN_BODY.setRotationPoint(-3F, 20F, -3F);
	      MAIN_BODY.setTextureSize(128, 128);
	      MAIN_BODY.mirror = true;
	      setRotation(MAIN_BODY, 0F, 0F, 0F);
	      MAIN_SUPPORT = new ModelRenderer(this, 0, 59);
	      MAIN_SUPPORT.addBox(0F, 0F, 0F, 4, 2, 4);
	      MAIN_SUPPORT.setRotationPoint(-2F, 18F, -2F);
	      MAIN_SUPPORT.setTextureSize(128, 128);
	      MAIN_SUPPORT.mirror = true;
	      setRotation(MAIN_SUPPORT, 0F, 0F, 0F);
	      TORQUE_ROT = new ModelRenderer(this, 0, 68);
	      TORQUE_ROT.addBox(-1F, -4F, -1F, 2, 4, 2);
	      TORQUE_ROT.setRotationPoint(0F, 18F, 0F);
	      TORQUE_ROT.setTextureSize(128, 128);
	      TORQUE_ROT.mirror = true;
	      setRotation(TORQUE_ROT, 0F, 0.7853982F, 0F);
	      CLAMP_1_ROT = new ModelRenderer(this, 20, 0);
	      CLAMP_1_ROT.addBox(-1F, -1F, -17F, 6, 6, 1);
	      CLAMP_1_ROT.setRotationPoint(0F, 13F, 0F);
	      CLAMP_1_ROT.setTextureSize(128, 128);
	      CLAMP_1_ROT.mirror = true;
	      setRotation(CLAMP_1_ROT, 0F, 0F, -2.356194F);
	      CLAMP_2_ROT = new ModelRenderer(this, 20, 0);
	      CLAMP_2_ROT.addBox(-1F, -1F, 0F, 6, 6, 1);
	      CLAMP_2_ROT.setRotationPoint(0F, 13F, 0F);
	      CLAMP_2_ROT.setTextureSize(128, 128);
	      CLAMP_2_ROT.mirror = true;
	      setRotation(CLAMP_2_ROT, 0F, 0F, -2.356194F);
	      COUNTERBALANCE_1_ROT = new ModelRenderer(this, 84, 0);
	      COUNTERBALANCE_1_ROT.addBox(1F, -4F, 1F, 4, 4, 8);
	      COUNTERBALANCE_1_ROT.setRotationPoint(0F, 14F, 0F);
	      COUNTERBALANCE_1_ROT.setTextureSize(128, 128);
	      COUNTERBALANCE_1_ROT.mirror = true;
	      setRotation(COUNTERBALANCE_1_ROT, 0F, 0F, 0F);
	      COUNTERBALANCE_2_ROT = new ModelRenderer(this, 84, 0);
	      COUNTERBALANCE_2_ROT.addBox(-5F, -4F, 1F, 4, 4, 8);
	      COUNTERBALANCE_2_ROT.setRotationPoint(0F, 14F, 0F);
	      COUNTERBALANCE_2_ROT.setTextureSize(128, 128);
	      COUNTERBALANCE_2_ROT.mirror = true;
	      setRotation(COUNTERBALANCE_2_ROT, 0F, 0F, 0F);
	      RAIL_1_ROT = new ModelRenderer(this, 65, 13);
	      RAIL_1_ROT.addBox(-5F, -1F, -17F, 4, 1, 18);
	      RAIL_1_ROT.setRotationPoint(0F, 14F, 0F);
	      RAIL_1_ROT.setTextureSize(128, 128);
	      RAIL_1_ROT.mirror = true;
	      setRotation(RAIL_1_ROT, 0F, 0F, 0.7853982F);
	      RAIL_2_ROT = new ModelRenderer(this, 65, 13);
	      RAIL_2_ROT.addBox(-5F, 0F, -17F, 4, 1, 18);
	      RAIL_2_ROT.setRotationPoint(0F, 14F, 0F);
	      RAIL_2_ROT.setTextureSize(128, 128);
	      RAIL_2_ROT.mirror = true;
	      setRotation(RAIL_2_ROT, 0F, 0F, 2.356194F);
	      TORQUE_SUPPORT_ROT = new ModelRenderer(this, 47, 17);
	      TORQUE_SUPPORT_ROT.addBox(-2F, 0F, -2F, 4, 2, 4);
	      TORQUE_SUPPORT_ROT.setRotationPoint(0F, 13F, 0F);
	      TORQUE_SUPPORT_ROT.setTextureSize(128, 128);
	      TORQUE_SUPPORT_ROT.mirror = true;
	      setRotation(TORQUE_SUPPORT_ROT, 0F, 0F, 0F);
	  }
	  
	  public void render(float f5)
	  {
	    SUPPORT_1.render(f5);
	    SUPPORT_2.render(f5);
	    SUPPORT_3.render(f5);
	    SUPPORT_4.render(f5);
	    SUPPORT_5.render(f5);
	    SUPPORT_6.render(f5);
	    MAIN_BODY.render(f5);
	    MAIN_SUPPORT.render(f5);
	    TORQUE_ROT.render(f5);
	    CLAMP_1_ROT.render(f5);
	    CLAMP_2_ROT.render(f5);
	    COUNTERBALANCE_1_ROT.render(f5);
	    COUNTERBALANCE_2_ROT.render(f5);
	    RAIL_1_ROT.render(f5);
	    RAIL_2_ROT.render(f5);
	    TORQUE_SUPPORT_ROT.render(f5);
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5);
	  }
}
