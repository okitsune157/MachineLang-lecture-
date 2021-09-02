package sep3.model.operation;
import sep3.model.CPU;

public class SvcOperation extends Operation {
	private CPU cpu;
	SvcOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
	public void operate() {
		// Bバスは使わない、Aバスを使用する
		useABus(true);
		useBBus(false);

		//PCの値をAバスへ流す
		cpu.getABusSelector().selectFrom(CPU.REG_R7);
		// Aバスの値をそのままSバスへ渡す
		//cpu.getSBus().setValue(InstructionSet.OP_THRA);
		cpu.getSBus().setValue(cpu.getABus().getValue());
		// Sバスの値をToオペランド(SP)+に書き込む
		writeBack(true);
	}
}
