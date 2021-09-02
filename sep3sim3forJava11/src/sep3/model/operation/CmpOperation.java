package sep3.model.operation;
import sep3.model.CPU;

// 通常のSUB命令用
public class CmpOperation extends Operation {
	private CPU cpu;
	CmpOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
	public void operate() {
		// AバスにMDRの値を、BバスへB0の値を出力する
		useABus(true);
		useBBus(true);

		// 両バスの値を引いてSバスに渡す。ToオペランドからFromオペランドをひく。
		int i = cpu.getABus().getValue();//T
		int j = cpu.getBBus().getValue();//F
		int o = i - j;

		// PSWの更新
		int p = psw_NZ(o);
		// オーバーフローするのは、iとjの符号ビットが異なり、jとoの符号ビットが異なる場合
		boolean diffMSBin  = bit(i, 0x8000) != bit(j, 0x8000);
		boolean diffMSBout = bit(j, 0x8000) != bit(o, 0x8000);

		System.out.println("CMP!"+i+"-"+j+"="+o);

		if (diffMSBin && diffMSBout)			{ p |= CPU.PSW_V; }
		if (bit(o, 0x10000))					{ p |= CPU.PSW_C; }
		cpu.getRegister(CPU.REG_PSW).setValue(p);
		writeBack(false);
		//Toオペランドへの書き込み無し
	}
}
