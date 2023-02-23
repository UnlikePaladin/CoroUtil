package com.corosus.coroutil.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextureAtlasSprite.class)
public interface TextureAtlasSpriteMixin {


    @Accessor
    TextureAtlasSprite.AnimatedTexture getAnimatedTexture();

    @Accessor
    int getWidth();

    @Accessor
    int getHeight();

    @Accessor
    NativeImage[] getMainImage();

}
