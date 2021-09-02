package sep3.model.operation;
import sep3.model.CPU;

public class XorOperation extends Operation {
    private CPU cpu;
    XorOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
    public void operate() {
        // AバスにMDRの値を、BバスへB0の値を出力する
        useABus(true);
        useBBus(true);

        // 両バスの値を足してSバスに渡す
        int i = cpu.getABus().getValue();
        int j = cpu.getBBus().getValue();
        int o = i ^ j;

        // PSWの更新
        int p = psw_NZ(o);//v=0になる
        // v=0,Cは変化無し
        p |= cpu.getRegister(CPU.REG_PSW).getValue() & 0x0001;			// C will not change
        cpu.getRegister(CPU.REG_PSW).setValue(p);

        // Sバスの値をToオペランドに書き込む
        writeBack(true);
    }
}
