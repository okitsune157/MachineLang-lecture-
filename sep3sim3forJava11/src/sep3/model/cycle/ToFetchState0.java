package sep3.model.cycle;

import sep3.Model;
import sep3.model.CPU;
import sep3.model.Decoder;
import sep3.model.operation.InstructionSet;

public class ToFetchState0 extends State{
    @Override
    public State clockstep(Model model) {
        //System.out.println("%% TF0 %%");
        // ステータスカウンタの設定。次の２行は、すべての状態において、最初に必ず記述すること
        CPU cpu = model.getCPU();
        cpu.getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_TF0);
        //モード指定確認
        int mode = cpu.getDecoder().getToMode();
        System.out.println(mode);
        switch(mode){
            case Decoder.MODE_D:// レジスタ
                // Tオペランドに指定されたレジスタの値を MAR, MDR へ送る
                // Tオペランドに指定されたレジスタの値をAバスへ送る
                cpu.getABusSelector().selectFrom(cpu.getDecoder().getToRegister());
                // Bバスへのゲートはすべて閉じる
                cpu.getBBusSelector().selectFrom();
                // ALUはAバスの値をそのままSバスへ流す
                cpu.getALU().operate(InstructionSet.OP_THRA);
                // Sバスの値をMAR, MDRへ送る
                cpu.getSBusSelector().selectTo(CPU.REG_MAR, CPU.REG_MDR);

                System.out.println("%% TF0 %%"+mode);

                // 次の状態へ
                return cpu.getStateFactory().getState(StateFactory.SC_EX0);
            case Decoder.MODE_I:// レジスタ間接
                // Tオペランドに指定されたレジスタの値を MAR, MDR へ送る
                // Tオペランドに指定されたレジスタの値をAバスへ送る
                cpu.getABusSelector().selectFrom(cpu.getDecoder().getToRegister());
                // Bバスへのゲートはすべて閉じる
                cpu.getBBusSelector().selectFrom();
                // ALUはAバスの値をそのままSバスへ流す
                cpu.getALU().operate(InstructionSet.OP_THRA);
                // Sバスの値をMAR, MDRへ送る
                cpu.getSBusSelector().selectTo(CPU.REG_MAR, CPU.REG_MDR);

                System.out.println("%% TF0 %%"+mode);

                // 次の状態へ
                return cpu.getStateFactory().getState(StateFactory.SC_TF1);
            case Decoder.MODE_MI:// プレデクリメントレジスタ間接

                System.out.println("%% TF0 %%"+mode);

                // 不正な命令
                return cpu.getStateFactory().getState(StateFactory.SC_ILL);
            case Decoder.MODE_IP:// ポストインクリメントレジスタ間接
                // Tオペランドに指定されたレジスタの値を MAR, MDR へ送る
                // Tオペランドに指定されたレジスタの値をAバスへ送る
                cpu.getABusSelector().selectFrom(cpu.getDecoder().getToRegister());
                // Bバスへのゲートはすべて閉じる
                cpu.getBBusSelector().selectFrom();
                // ALUはAバスの値をそのままSバスへ流す
                cpu.getALU().operate(InstructionSet.OP_THRA);
                // Sバスの値をMAR, MDRへ送る
                cpu.getSBusSelector().selectTo(CPU.REG_MAR, CPU.REG_MDR);

                System.out.println("%% TF0 %%"+mode);

                // 次の状態へ
                return cpu.getStateFactory().getState(StateFactory.SC_TF1);
        }
        return cpu.getStateFactory().getState(StateFactory.SC_ILL);
    }
}
