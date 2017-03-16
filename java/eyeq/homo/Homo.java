package eyeq.homo;

import eyeq.homo.client.renderer.entity.RenderHomo;
import eyeq.homo.client.renderer.entity.RenderLes;
import eyeq.homo.entity.passive.EntityHomo;
import eyeq.homo.entity.passive.EntityLes;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.USoundCreator;
import eyeq.util.client.resource.gson.SoundResourceManager;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.common.registry.UEntityRegistry;
import eyeq.util.common.registry.USoundEventRegistry;
import eyeq.util.world.biome.BiomeUtils;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Biomes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.List;

import static eyeq.homo.Homo.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
public class Homo {
    public static final String MOD_ID = "eyeq_homo";

    @Mod.Instance(MOD_ID)
    public static Homo instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static final String I18n_HOMO = "msg.homo.txt";
    public static final String I18n_LES = "msg.les.txt";

    public static SoundEvent entityHomoAmbient;
    public static SoundEvent entityLesAmbient;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        registerEntities();
        registerSoundEvents();
        if(event.getSide().isServer()) {
            return;
        }
        registerEntityRenderings();
        createFiles();
    }

    public static void registerEntities() {
        UEntityRegistry.registerModEntity(resource, EntityHomo.class, "Homo", 0, instance, 0xFAFAFA, 0xFAFAFA);
        UEntityRegistry.registerModEntity(resource, EntityLes.class, "Les", 1, instance, 0xFAFAFA, 0xFAFAFA);

        List<Biome> biomes = BiomeUtils.getSpawnBiomes(EntitySheep.class, EnumCreatureType.CREATURE);
        EntityRegistry.addSpawn(EntityHomo.class, 5, 2, 4, EnumCreatureType.CREATURE, biomes.toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityHomo.class, 2, 2, 20, EnumCreatureType.CREATURE, Biomes.MUSHROOM_ISLAND, Biomes.MUSHROOM_ISLAND_SHORE);

        EntityRegistry.addSpawn(EntityLes.class, 5, 2, 4, EnumCreatureType.CREATURE, biomes.toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityLes.class, 2, 2, 20, EnumCreatureType.CREATURE, Biomes.MUTATED_FOREST);
    }

    public static void registerSoundEvents() {
        entityHomoAmbient = new SoundEvent(resource.createResourceLocation("homo"));
        entityLesAmbient = new SoundEvent(resource.createResourceLocation("les"));

        USoundEventRegistry.registry(entityHomoAmbient);
        USoundEventRegistry.registry(entityLesAmbient);
    }

    @SideOnly(Side.CLIENT)
    public static void registerEntityRenderings() {
        RenderingRegistry.registerEntityRenderingHandler(EntityHomo.class, RenderHomo::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLes.class, RenderLes::new);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-Homo");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, I18n_HOMO, "┌(┌^o^)┐homo...");
        language.register(LanguageResourceManager.JA_JP, I18n_HOMO, "┌(┌^o^)┐ﾎﾓｫ…");
        language.register(LanguageResourceManager.EN_US, I18n_LES, "┗(┗˘o˘)┛les...");
        language.register(LanguageResourceManager.JA_JP, I18n_LES, "┗(┗˘o˘)┛ﾚｽﾞｩ…");

        language.register(LanguageResourceManager.EN_US, EntityHomo.class, "┌(┌^o^)┐");
        language.register(LanguageResourceManager.JA_JP, EntityHomo.class, "┌(┌^o^)┐");
        language.register(LanguageResourceManager.EN_US, EntityLes.class, "┗(┗˘o˘)┛");
        language.register(LanguageResourceManager.JA_JP, EntityLes.class, "┗(┗˘o˘)┛");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        SoundResourceManager sound = new SoundResourceManager();

        sound.register(entityHomoAmbient, SoundCategory.AMBIENT.getName());
        sound.register(entityLesAmbient, SoundCategory.AMBIENT.getName());

        USoundCreator.createSoundJson(project, MOD_ID, sound);
    }
}
