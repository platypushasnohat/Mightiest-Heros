package com.platypushasnohat.mightiest_heroes;

import com.platypushasnohat.mightiest_heroes.datagen.client.HeroItemModelProvider;
import com.platypushasnohat.mightiest_heroes.datagen.client.HeroLanguageProvider;
import com.platypushasnohat.mightiest_heroes.datagen.client.HeroSoundDefinitionsProvider;
import com.platypushasnohat.mightiest_heroes.registry.HeroArmorMaterials;
import com.platypushasnohat.mightiest_heroes.registry.HeroBlocks;
import com.platypushasnohat.mightiest_heroes.registry.HeroItemProperties;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Mod(MightiestHeroes.MOD_ID)
public class MightiestHeroes {

    public static final String MOD_ID = "mightiest_heroes";

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path.toLowerCase(Locale.ROOT));
    }

    public MightiestHeroes(IEventBus modEventBus, ModContainer modContainer) {
        HeroItems.ITEMS.register(modEventBus);
        HeroBlocks.BLOCKS.register(modEventBus);
        HeroArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        MightiestHeroesTab.CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::dataSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(HeroItemProperties::registerItemProperties);
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean server = event.includeServer();
        boolean client = event.includeClient();

        generator.addProvider(client, new HeroItemModelProvider(output, helper));
        generator.addProvider(client, new HeroSoundDefinitionsProvider(output, helper));
        generator.addProvider(client, new HeroLanguageProvider(output));
    }
}
