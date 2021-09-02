package sep3.model.operation;
import sep3.model.CPU;

public class RbvOperation extends Operation {
	private CPU cpu;
	RbvOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
	public void operate() {
		//PSWの値をとってくる
		int p = cpu.getRegister(CPU.REG_PSW).getValue();
		System.out.println("RBV!"+bit(p,CPU.PSW_V));
		if(bit(p,CPU.PSW_V)) {
			// 変位の計算(PSW変化なし)
			// AバスにMDRの値を、BバスへB0の値を出力する
			// 両バスの値を足してSバスに渡す。PSWの更新はしない
			// Sバスの値をToオペランドに書き込む
			cpu.getALU().operate(InstructionSet.OP_ADD2);
		}else{
			cpu.getALU().operate(InstructionSet.OP_NOP);
		}
	}
}
