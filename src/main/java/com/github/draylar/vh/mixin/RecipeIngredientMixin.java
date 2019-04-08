package com.github.draylar.vh.mixin;

import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Ingredient.class)
public class RecipeIngredientMixin
{
//    @Inject(method = "method_17873", at = @At(value = "HEAD"), cancellable = true)
////    private static void entryFromJson(Identifier identifier, CallbackInfoReturnable<JsonSyntaxException> cir)
////    {
////        System.out.println(identifier);
////        if(identifier.getNamespace().equals("lil-tater"))
////        {
////            System.out.println("canceling");
////            cir.setReturnValue(new JsonSyntaxException("tyyee"));
////            cir.cancel();
////        }
////    }
}
