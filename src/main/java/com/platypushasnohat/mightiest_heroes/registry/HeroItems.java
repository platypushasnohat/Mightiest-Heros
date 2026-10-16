package com.platypushasnohat.mightiest_heroes.registry;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.items.GoblinArmorItem;
import com.platypushasnohat.mightiest_heroes.items.SuperSoldierArmorItem;
import com.platypushasnohat.mightiest_heroes.items.VibraniumShieldItem;
import com.platypushasnohat.sinew.item.DisabledItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class HeroItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MightiestHeroes.MOD_ID);
    public static final List<DeferredItem<? extends Item>> ITEM_TRANSLATIONS = new ArrayList<>();

    public static final DeferredItem<Item> VIBRANIUM_SHIELD = registerItem("vibranium_shield", () -> new VibraniumShieldItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> DISABLED_VIBRANIUM_SHIELD = registerItemNoLang("disabled_vibranium_shield", () -> new DisabledItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> RAW_VIBRANIUM = registerItem("raw_vibranium", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VIBRANIUM_INGOT = registerItem("vibranium_ingot", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SUPER_SOLDIER_MASK = registerItem("super_soldier_mask", () -> new SuperSoldierArmorItem(ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.HELMET.getDurability(30))));
    public static final DeferredItem<Item> SUPER_SOLDIER_CHESTPLATE = registerItem("super_soldier_chestplate", () -> new SuperSoldierArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.CHESTPLATE.getDurability(30))));
    public static final DeferredItem<Item> SUPER_SOLDIER_LEGGINGS = registerItem("super_soldier_leggings", () -> new SuperSoldierArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.LEGGINGS.getDurability(30))));
    public static final DeferredItem<Item> SUPER_SOLDIER_BOOTS = registerItem("super_soldier_boots", () -> new SuperSoldierArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.BOOTS.getDurability(30))));

    public static final DeferredItem<Item> GOBLIN_MASK = registerItem("goblin_mask", () -> new GoblinArmorItem(ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.HELMET.getDurability(30))));
    public static final DeferredItem<Item> GOBLIN_CHESTPLATE = registerItem("goblin_chestplate", () -> new GoblinArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.CHESTPLATE.getDurability(30))));
    public static final DeferredItem<Item> GOBLIN_LEGGINGS = registerItem("goblin_leggings", () -> new GoblinArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.LEGGINGS.getDurability(30))));
    public static final DeferredItem<Item> GOBLIN_BOOTS = registerItem("goblin_boots", () -> new GoblinArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.BOOTS.getDurability(30))));

    private static <I extends Item> DeferredItem<I> registerItem(String name, Supplier<? extends I> supplier) {
        DeferredItem<I> item = ITEMS.register(name, supplier);
        ITEM_TRANSLATIONS.add(item);
        return item;
    }

    private static <I extends Item> DeferredItem<I> registerItemNoLang(String name, Supplier<? extends I> supplier) {
        return ITEMS.register(name, supplier);
    }
}