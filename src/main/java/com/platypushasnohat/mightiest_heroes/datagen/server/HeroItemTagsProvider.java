package com.platypushasnohat.mightiest_heroes.datagen.server;

import com.platypushasnohat.mightiest_heroes.MightiestHeroes;
import com.platypushasnohat.mightiest_heroes.registry.HeroItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class HeroItemTagsProvider extends ItemTagsProvider {

	public HeroItemTagsProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagLookup<Block>> lookup, ExistingFileHelper helper) {
		super(output, provider, lookup, MightiestHeroes.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {

		this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(
				HeroItems.VIBRANIUM_SHIELD.get()
		);
		this.tag(ItemTags.VANISHING_ENCHANTABLE).add(
				HeroItems.VIBRANIUM_SHIELD.get()
		);
		this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(
				HeroItems.VIBRANIUM_SHIELD.get()
		);
		this.tag(ItemTags.SWORD_ENCHANTABLE).add(
				HeroItems.VIBRANIUM_SHIELD.get()
		);

		this.tag(ItemTags.HEAD_ARMOR).add(
				HeroItems.SUPER_SOLDIER_MASK.get()
		);
		this.tag(ItemTags.CHEST_ARMOR).add(
				HeroItems.SUPER_SOLDIER_CHESTPLATE.get()
		);
		this.tag(ItemTags.LEG_ARMOR).add(
				HeroItems.SUPER_SOLDIER_LEGGINGS.get()
		);
		this.tag(ItemTags.FOOT_ARMOR).add(
				HeroItems.SUPER_SOLDIER_BOOTS.get()
		);

		this.tag(Tags.Items.TOOLS_SHIELD).add(
				HeroItems.VIBRANIUM_SHIELD.get()
		);
	}
}