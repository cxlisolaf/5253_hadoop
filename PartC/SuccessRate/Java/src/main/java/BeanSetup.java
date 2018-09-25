import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;


public class BeanSetup implements Writable{

    private String item_id;
    private int click;
    private int buy;

    // flag=0 is click, flag =1 is buy
    private String flag;

    public BeanSetup(){

    }

    public void set(String item_id){

        this.item_id = item_id;
    }

    public void setClick(int click){
        this.click = click;
    }
    public int getClick(){
        return click;
    }


    public void setBuy(int buy){
        this.buy = buy;
    }
    public int getBuy(){
        return buy;
    }

    public void setFlag(String flag){
        this.flag = flag;
    }
    public String getFlag(){
        return flag;
    }

    public void write(DataOutput output)
        throws IOException{
        output.writeInt(click);
        output.writeInt(buy);
        output.writeUTF(item_id);
        output.writeUTF(flag);

    }

    public void readFields(DataInput in)
        throws IOException{
        this.item_id =  in.readUTF();
        this.click = in.readInt();
        this.buy = in.readInt();
        this.flag = in.readUTF();
    }


}
