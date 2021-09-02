package sep3.model.cycle;

import sep3.*;
import sep3.model.*;
import sep3.model.operation.*;

public class FromFetchState0 extends State{
    @Override
    public State clockstep(Model model) {
        //System.out.println("%% FF0 %%");
        // ステータスカウンタの設定。次の２行は、すべての状態において、最初に必ず記述すること
        CPU cpu = model.getCPU();
        cpu.getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_FF0);
        //モード指定確認
        int mode = cpu.getDecoder().getFromMode();
        switch(mode){
            case Decoder.MODE_D:// レジスタ
                // Fオペランドに指定されたレジスタの値を MAR, MDR へ送る
                // Fオペランドに指定されたレジスタの値をAバスへ送る
                cpu.getABusSelector().selectFrom(cpu.getDecoder().getFromRegister());
                // Bバスへのゲートはすべて閉じる
                cpu.getBBusSelector().selectFrom();
                // ALUはAバスの値をそのままSバスへ流す
                cpu.getALU().operate(InstructionSet.OP_THRA);
                // Sバスの値をMAR, MDRへ送る
                cpu.getSBusSelector().selectTo(CPU.REG_MAR, CPU.REG_MDR);
                // 次の状態へ
                return cpu.getStateFactory().getState(StateFactory.SC_FF2);
            case Decoder.MODE_I:// レジスタ間接
                // Fオペランドに指定されたレジスタの値を MAR, MDR へ送る
                // Fオペランドに指定されたレジスタの値をAバスへ送る
                cpu.getABusSelector().selectFrom(cpu.getDecoder().getFromRegister());
                // Bバスへのゲートはすべて閉じる
                cpu.getBBusSelector().selectFrom();
                // ALUはAバスの値をそのままSバスへ流す
                cpu.getALU().operate(InstructionSet.OP_THRA);
                // Sバスの値をMAR, MDRへ送る
                cpu.getSBusSelector().selectTo(CPU.REG_MAR, CPU.REG_MDR);
                // 次の状態へ
                return cpu.getStateFactory().getState(StateFactory.SC_FF1);
            case Decoder.MODE_MI:// プレデクリメントレジスタ間接
                // Fオペランドに指定されたレジスタの値を-1して戻す
                cpu.getABusSelector().selectFrom(cpu.getDecoder().getFromRegister());
                cpu.getBBusSelector().selectFrom();
                cpu.getALU().operate(InstructionSet.OP_DEC);
                cpu.getSBusSelector().selectTo(cpu.getDecoder().getFromRegister());
                // Fオペランドに指定されたレジスタの値を MAR, MDR へ送る
                // Sバスの値をMAR, MDRへ送る
                cpu.getSBusSelector().selectTo(CPU.REG_MAR, CPU.REG_MDR);
                // 次の状態へ
                return cpu.getStateFactory().getState(StateFactory.SC_FF1);
            case Decoder.MODE_IP:// ポストインクリメントレジスタ間接
                // Fオペランドに指定されたレジスタの値を MAR, MDR へ送る
                // Fオペランドに指定されたレジスタの値をAバスへ送る
                cpu.getABusSelector().selectFrom(cpu.getDecoder().getFromRegister());
                // Bバスへのゲートはすべて閉じる
                cpu.getBBusSelector().selectFrom();
                // ALUはAバスの値をそのままSバスへ流す
                cpu.getALU().operate(InstructionSet.OP_THRA);
                // Sバスの値をMAR, MDRへ送る
                cpu.getSBusSelector().selectTo(CPU.REG_MAR, CPU.REG_MDR);
                // 次の状態へ
                return cpu.getStateFactory().getState(StateFactory.SC_FF1);
        }
        return cpu.getStateFactory().getState(StateFactory.SC_ILL);
    }
}
