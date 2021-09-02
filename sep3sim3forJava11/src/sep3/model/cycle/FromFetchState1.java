package sep3.model.cycle;

import sep3.*;
import sep3.model.*;
import sep3.model.operation.*;

public class FromFetchState1 extends State {
    @Override
    public State clockstep(Model model) {
        System.out.println("%% FF1 %%");
        // ステータスカウンタの設定。次の２行は、すべての状態において、最初に必ず記述すること
        CPU cpu = model.getCPU();
        cpu.getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_FF1);

        //モード指定確認
        int mode = cpu.getDecoder().getFromMode();

        // MARに格納されている番地から命令を読み出してISRへ送る
        // MAR をアドレスバスに流す
        model.getAddrBusSelector().selectFrom(CPU.REG_MAR);
        // メモリを読み出してデータバスへ出力
        model.getMemory().access(Memory.MEM_RD);
        // データバスの値をMDRへ送る
        model.getDataBusSelector().selectTo(CPU.REG_MDR);

        if(mode == Decoder.MODE_IP){
            // 命令を読み出している間に、Fオペランドに指定されたレジスタに格納された値をインクリメント
            // Fオペランドに指定されたレジスタの値をAバスへ送る
            cpu.getABusSelector().selectFrom(cpu.getDecoder().getFromRegister());
            // Bバスへのゲートはすべて閉じる
            cpu.getBBusSelector().selectFrom();
            // ALUはAバスの値を+1してSバスに流す
            cpu.getALU().operate(InstructionSet.OP_INC);
            // Sバスの値をFオペランドに指定されたレジスタに送る
            cpu.getSBusSelector().selectTo(cpu.getDecoder().getFromRegister());//?
        }
        // 次の状態へ
        return cpu.getStateFactory().getState(StateFactory.SC_FF2);
    }
}
