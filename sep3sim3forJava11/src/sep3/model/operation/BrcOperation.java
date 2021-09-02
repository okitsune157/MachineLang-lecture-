package sep3.model.operation;
import sep3.model.CPU;

public class BrcOperation extends Operation {
	private CPU cpu;
	BrcOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
	public void operate() {
		//PSWの値をとってくる
		int p = cpu.getRegister(CPU.REG_PSW).getValue();
		System.out.println("BRC!"+bit(p,CPU.PSW_C));
		if(bit(p,CPU.PSW_C)) {
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
