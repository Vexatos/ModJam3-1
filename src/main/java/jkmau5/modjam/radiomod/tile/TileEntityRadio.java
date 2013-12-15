package jkmau5.modjam.radiomod.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 11:05
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class TileEntityRadio extends TileEntity {
    private String connectedBroadcastStation;

    public String getConnectedBroadcastStation() {
        return this.connectedBroadcastStation;
    }

    public void setConnectedBroadcastStation(String connectedBroadcastStation) {
        this.connectedBroadcastStation = connectedBroadcastStation;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        setConnectedBroadcastStation(tagCompound.getString("connectedBroadcastStation"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setString("connectedBroadcastStation", getConnectedBroadcastStation());
    }

    @Override
    public Packet getDescriptionPacket() {
        Packet132TileEntityData tilePacket = new Packet132TileEntityData();
        writeToNBT(tilePacket.data);
        return tilePacket;
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        readFromNBT(pkt.data);
    }
}