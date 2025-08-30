package com.infinitum;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Init implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("potere");

	public static final List<RegistryType> potere_items = new ArrayList<>();

	@Override
		public void onInitialize() {

			
			potere_items.add(itemEntries.power_crystal);

			for (RegistryType reg : potere_items){
				Registry.register(Registries.ITEM, new Identifier("potere", reg.name), reg.item);
			}

			// This entrypoint is suitable for setting up client-specific logic, such as rendering.

			//ServerPlayNetworking.registerGlobalReceiver(UsePower.ACTIVATE_POTERE_POWER, );
			// Register the handler on the server
			ServerPlayNetworking.registerGlobalReceiver(UsePower.ACTIVATE_POTERE_POWER, new ServerPlayNetworking.PlayChannelHandler() {
				@Override
				public void receive(MinecraftServer server, ServerPlayerEntity player,
									ServerPlayNetworkHandler handler, PacketByteBuf buf,
									PacketSender responseSender) {
					// Extract data from the packet
					//String action = buf.readString();
			
					// Always run game logic on the server thread
					server.execute(() -> {
						if (player.getOffHandStack().getNbt() != null){
							NbtCompound crystal_nbt = player.getOffHandStack().getNbt();
							server.getPlayerManager().broadcast(
								Text.translatable("chat.potere.activate_power",crystal_nbt.get("power_name").asString()), true
								//Text.of(player.getName().getString() + " pressed: " + action), false
							);
						}
					});
				}
			});
		}
}