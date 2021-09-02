package sep3.model.operation;
import sep3.model.CPU;

public class BrvOperation extends Operation {
	private CPU cpu;
	BrvOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
	public void operate() {
		//PSWの値をとってくる
		int p = cpu.getRegister(CPU.REG_PSW).getValue();
		System.out.println("BRV!"+bit(p,CPU.PSW_V));
		if(bit(p,CPU.PSW_V)) {
			cpu.getALU().operate(InstructionSet.OP_JMP);
//			// Aバスは使わない、BバスへB0の値を出力する
//			useABus(false);
//			useBBus(true);
//			// Bバスの値をそのままSバスへ渡す
//			cpu.getSBus().setValue(cpu.getBBus().getValue());
//			// Sバスの値をToオペランドに書き込む
//			writeBack(true);
		}else{
			cpu.getALU().operate(InstructionSet.OP_NOP);
		}
	}
}
