package sep3.model.operation;
import sep3.model.CPU;

public class RitOperation extends Operation {
	private CPU cpu;
	RitOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
	public void operate() {
		// Aバスは使わない、BバスへB0の値を出力する
		useABus(false);
		useBBus(true);

		// Bバスの値をそのままSバスへ渡す
		cpu.getSBus().setValue(cpu.getBBus().getValue());

		// Sバスの値をToオペランド(SP)+に書き込む
		writeBack(true);
	}
}
