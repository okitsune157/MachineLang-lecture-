package sep3.model.operation;
import sep3.model.CPU;

public class BrnOperation extends Operation {
	private CPU cpu;
	BrnOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
	public void operate() {
		//PSWの値をとってくる
		int p = cpu.getRegister(CPU.REG_PSW).getValue();
		System.out.println("BRN!"+bit(p,CPU.PSW_N));
		if(bit(p,CPU.PSW_N)) {
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
