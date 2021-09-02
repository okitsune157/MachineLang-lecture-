package sep3.model.operation;
import sep3.model.CPU;

public class JsrOperation extends Operation {
	private CPU cpu;
	JsrOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
	public void operate() {
		// ジャンプ先の計算はEX1、ここでは戻り番地をスタックに格納するだけ
		// Bバスは使わない、Aバスを使用する
		useABus(true);
		useBBus(false);
		//PCの値をAバスへ流す
		cpu.getABusSelector().selectFrom(CPU.REG_R7);
		// Aバスの値をそのままSバスへ渡す
		cpu.getSBus().setValue(cpu.getABus().getValue());

		// Sバスの値をToオペランド(SP)+に書き込む
		writeBack(true);
	}
}
