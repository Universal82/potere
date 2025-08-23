package com.infinitum;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;

import org.lwjgl.glfw.GLFW;
import net.fabricmc.api.ClientModInitializer;

public class ClientInit implements ClientModInitializer {

	private static KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.potere.activate_power", 
			InputUtil.Type.KEYSYM, 
			GLFW.GLFW_KEY_R, 
			"key.category.potere"
		));

	

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
    		while (keyBinding.wasPressed()) {
				//client.player.sendMessage(net.minecraft.text.Text.translatable("chat.potere.activate_power"), false);
				// send server the use power packet
				PacketByteBuf buf = PacketByteBufs.create();
        		buf.writeString("activate");
        		ClientPlayNetworking.send(UsePower.ACTIVATE_POTERE_POWER, buf);
    		}
		});
	}
}