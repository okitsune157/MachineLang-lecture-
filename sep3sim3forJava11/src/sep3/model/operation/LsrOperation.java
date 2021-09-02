package sep3.model.operation;
import sep3.model.CPU;

public class LsrOperation extends Operation {
    private CPU cpu;
    LsrOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
    public void operate() {
        // AバスもBバスも使わない
        useABus(true);
        useBBus(false);
        // Aバスの値を右論理シフト
        int i = cpu.getABus().getValue();
        int o = i >>> 1;
        // PSWの更新
        int p = psw_NZ(o);
        // オーバーフローはしない
        //計算前の最下位ビットがCビットに反映
        if (bit(i, 0x0001))					{ p |= CPU.PSW_C; }
        cpu.getRegister(CPU.REG_PSW).setValue(p);
        // キャリーがあったら捨てて、Sバスの値をToオペランドに書き込む
        cpu.getSBus().setValue(o & 0xFFFF);//AND
        writeBack(true);
    }
}
