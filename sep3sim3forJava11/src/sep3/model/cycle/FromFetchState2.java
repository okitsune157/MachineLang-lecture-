package sep3.model.cycle;

import sep3.*;
import sep3.model.*;
import sep3.model.operation.*;

public class FromFetchState2 extends State{
    @Override
    public State clockstep(Model model) {
        System.out.println("%% FF2 %%");
        // ステータスカウンタの設定。次の２行は、すべての状態において、最初に必ず記述すること
        CPU cpu = model.getCPU();
        cpu.getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_FF2);

        // PC の値を MAR, MDR へ送る

        // MDRの値をAバスへ
        cpu.getABusSelector().selectFrom(CPU.REG_MDR);
        // Bバスへのゲートはすべて閉じる
        // cpu.getBBusSelector().selectFrom();
        // ALUはAバスの値そのままSバスへ流す
        cpu.getALU().operate(InstructionSet.OP_THRA);
        // Sバスの値をB0へ送る
        cpu.getSBusSelector().selectTo(CPU.REG_B0);

        // 次の状態へ
        return cpu.getStateFactory().getState(StateFactory.SC_TF0);
    }
}
