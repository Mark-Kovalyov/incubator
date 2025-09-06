package mayton.tinyasm.asm;

import java.util.ArrayList;
import java.util.List;

public class FPUBuilderSample {

    List<CpuCommand> cpuCommands;

    public class FPUBuilderSampleBuilder {

        public FPUBuilderSampleBuilder() {
            cpuCommands = new ArrayList<>();
        }

        public FPUBuilderSampleBuilder addGenericFpuCommand(CpuCommand cpuCommand) {
            cpuCommands.add(cpuCommand);
            return this;
        }

        public FPUBuilderSampleBuilder fld() {
            cpuCommands.add(new CpuCommand("fld", ""));
            return this;
        }

        FPUBuilderSample build() {
            return new FPUBuilderSample();
        }

    }


}
