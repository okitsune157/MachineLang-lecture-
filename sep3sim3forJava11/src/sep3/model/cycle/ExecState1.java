package sep3.model.cycle;

import sep3.*;
import sep3.model.*;
import sep3.model.operation.*;

public class ExecState1 extends State {
	@Override
	public State clockstep(Model model) {
		System.out.println("State EX1");
		CPU cpu = model.getCPU();
		cpu.getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_EX1);

				//MDRからメモリへ書き込み
		model.getAddrBusSelector().selectFrom(CPU.REG_MAR);
		model.getDataBusSelector().selectFrom(CPU.REG_MDR);
		model.getMemory().MoveAccess(Memory.MEM_WR);

		// サブルーチンコール命令の場合、ジャンプ先アドレスをPCに格納
		final int op = cpu.getDecoder().getOpCode();
		if ((op & 0x2C) == 0x2C) {
			//B0の値をBバスへ流す
			cpu.getBBusSelector().selectFrom(CPU.REG_B0);
			switch (op) {
				case InstructionSet.OP_JSR:
					// SVCと同じ処理
				case InstructionSet.OP_SVC:
					// 絶対ジャンプなのでBバスを素通し
					cpu.getALU().operate(InstructionSet.OP_THRB);
					break;//次のケースは実行しない
				case InstructionSet.OP_RJS:
					// 相対ジャンプなのでAバスにR7を流す
					cpu.getABusSelector().selectFrom(CPU.REG_R7);
					// 相対ジャンプなのでB0とR7を足す
					cpu.getALU().operate(InstructionSet.OP_ADD2);
			}
			cpu.getSBusSelector().selectTo(CPU.REG_R7);
		}

		return cpu.getStateFactory().getState(StateFactory.SC_IF0);
	}
}
