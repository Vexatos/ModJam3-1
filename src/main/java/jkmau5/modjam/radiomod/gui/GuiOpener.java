package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jkmau5.modjam.radiomod.network.PacketOpenGui;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiOpener {

    @SideOnly(Side.CLIENT)
    public static void openGuiCallback(EnumGui gui){
        if(gui == EnumGui.MEDIA_PLAYER){
            Minecraft.getMinecraft().displayGuiScreen(new GuiMediaPlayer());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void openGuiCallback(EnumGui gui, int x, int y, int z){
        if(gui == EnumGui.BROADCASTER_BLOCK){
            World world = Minecraft.getMinecraft().thePlayer.worldObj;
            TileEntity tempTile = world.getBlockTileEntity(x, y, z);
            if(tempTile == null || !(tempTile instanceof TileEntityBroadcaster))
                return;
            TileEntityBroadcaster radio = (TileEntityBroadcaster) tempTile;
            Minecraft.getMinecraft().displayGuiScreen(new GuiBroadcaster(x, y, z, radio.getRadioName()));
        }else if(gui == EnumGui.RADIO_BLOCK){
            World world = Minecraft.getMinecraft().theWorld;
            TileEntity tempTile = world.getBlockTileEntity(x, y, z);
            if(tempTile == null || !(tempTile instanceof TileEntityRadio))
                return;

            Minecraft.getMinecraft().displayGuiScreen(new GuiMediaPlayer((TileEntityRadio) tempTile));
        }else if(gui == EnumGui.PLAYLIST_BLOCK){
            World world = Minecraft.getMinecraft().theWorld;
            TileEntity tempTile = world.getBlockTileEntity(x, y, z);
            if(tempTile == null || !(tempTile instanceof TileEntityPlaylist))
                return;

            Minecraft.getMinecraft().displayGuiScreen(new GuiPlaylist((TileEntityPlaylist) tempTile));
        }
    }

    public static void openGuiOnClient(EnumGui gui, EntityPlayer player){
        PacketDispatcher.sendPacketToPlayer(new PacketOpenGui(gui).getPacket(), (Player) player);
    }

    public static void openGuiOnClient(EnumGui gui, EntityPlayer player, int x, int y, int z){
        PacketDispatcher.sendPacketToPlayer(new PacketOpenGui(gui, x, y, z).getPacket(), (Player) player);
    }
}
