package buildcraft.api.enums;

import buildcraft.api.properties.BuildCraftProperties;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

public enum EnumMachineState implements IStringSerializable {
    OFF,
    ON,
    DONE;

    public static EnumMachineState getType(IBlockState state) {
        return (EnumMachineState) state.getValue(BuildCraftProperties.MACHINE_STATE);
    }

    @Override
    public String getName() {
        return name();
    }
}
