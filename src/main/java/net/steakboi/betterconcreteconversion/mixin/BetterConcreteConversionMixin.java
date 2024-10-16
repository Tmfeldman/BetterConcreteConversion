package net.steakboi.betterconcreteconversion.mixin;

import net.minecraft.world.entity.item.ItemEntity;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(ItemEntity.class)
public abstract class BetterConcreteConversionMixin {
    private static final Map<Item, Item> concreteConversionMap = new HashMap<>() {{
        put(Items.WHITE_CONCRETE_POWDER, Items.WHITE_CONCRETE);
        put(Items.ORANGE_CONCRETE_POWDER, Items.ORANGE_CONCRETE);
        put(Items.MAGENTA_CONCRETE_POWDER, Items.MAGENTA_CONCRETE);
        put(Items.LIGHT_BLUE_CONCRETE_POWDER, Items.LIGHT_BLUE_CONCRETE);
        put(Items.YELLOW_CONCRETE_POWDER, Items.YELLOW_CONCRETE);
        put(Items.LIME_CONCRETE_POWDER, Items.LIME_CONCRETE);
        put(Items.PINK_CONCRETE_POWDER, Items.PINK_CONCRETE);
        put(Items.GRAY_CONCRETE_POWDER, Items.GRAY_CONCRETE);
        put(Items.LIGHT_GRAY_CONCRETE_POWDER, Items.LIGHT_GRAY_CONCRETE);
        put(Items.CYAN_CONCRETE_POWDER, Items.CYAN_CONCRETE);
        put(Items.PURPLE_CONCRETE_POWDER, Items.PURPLE_CONCRETE);
        put(Items.BLUE_CONCRETE_POWDER, Items.BLUE_CONCRETE);
        put(Items.BROWN_CONCRETE_POWDER, Items.BROWN_CONCRETE);
        put(Items.GREEN_CONCRETE_POWDER, Items.GREEN_CONCRETE);
        put(Items.RED_CONCRETE_POWDER, Items.RED_CONCRETE);
        put(Items.BLACK_CONCRETE_POWDER, Items.BLACK_CONCRETE);
    }};

    @Inject(method = "tick()V", at = @At("TAIL"), cancellable = true)
    protected void ConvertConcretePowder(CallbackInfo ci) {
        try {
            ItemEntity this_item_entity = (ItemEntity) (Object) this;
            Level level = this_item_entity.level();
            if (!level.isClientSide()) {
                ItemStack current_stack = this_item_entity.getItem();
                if (current_stack.getItem() instanceof BlockItem) {
                    BlockItem this_block = (BlockItem) current_stack.getItem();
                    if (concreteConversionMap.containsKey(this_block)) {
                        if (this_item_entity.isInWater()) {
                            int count = current_stack.getCount();
                            this_item_entity.setItem(new ItemStack(concreteConversionMap.get(this_block), count));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to get level from block");
        }
    }
}