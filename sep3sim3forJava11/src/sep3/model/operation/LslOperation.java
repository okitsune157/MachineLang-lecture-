package sep3.model.operation;
import sep3.model.CPU;

public class LslOperation extends Operation {
    private CPU cpu;
    LslOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
    public void operate() {
        // AバスもBバスも使わない?
        useABus(true);
        useBBus(false);
        // Aバスの値を左論理シフト
        int i = cpu.getABus().getValue();
        int o = i << 1;
        // PSWの更新
        int p = psw_NZ(o);
        // オーバーフローするのは、iの符号ビットが、oの符号ビットと異なる場合
        if (bit(o, 0x10000))					{ p |= CPU.PSW_C; }
        cpu.getRegister(CPU.REG_PSW).setValue(p);
        // キャリーがあったら捨てて、Sバスの値をToオペランドに書き込む
        cpu.getSBus().setValue(o & 0xFFFF);//&ってORだっけ？<-ANDだった！
        writeBack(true);
    }
}
