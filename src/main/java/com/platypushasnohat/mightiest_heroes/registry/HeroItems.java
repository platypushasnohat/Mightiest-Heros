package com.platypushasnohat.mightiest_heroes.registry;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.items.VibraniumShieldItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class HeroItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MightiestHeroes.MOD_ID);
    public static final List<DeferredItem<? extends Item>> ITEM_TRANSLATIONS = new ArrayList<>();

    public static final DeferredItem<Item> VIBRANIUM_SHIELD = registerItem("vibranium_shield", () -> new VibraniumShieldItem(new Item.Properties().durability(945)));

    public static final DeferredItem<Item> RAW_VIBRANIUM = registerItem("raw_vibranium", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VIBRANIUM_INGOT = registerItem("vibranium_ingot", () -> new Item(new Item.Properties()));

    private static <I extends Item> DeferredItem<I> registerItem(String name, Supplier<? extends I> supplier) {
        DeferredItem<I> item = ITEMS.register(name, supplier);
        ITEM_TRANSLATIONS.add(item);
        return item;
    }

    private static <I extends Item> DeferredItem<I> registerItemNoLang(String name, Supplier<? extends I> supplier) {
        return ITEMS.register(name, supplier);
    }
}