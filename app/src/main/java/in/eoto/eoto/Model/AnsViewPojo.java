package in.eoto.eoto.Model;

/**
 * Created by Ashu on 20-Mar-18.
 */

public class AnsViewPojo {
    String body, ans, date;
    public  AnsViewPojo(String body, String ans, String date){
        this.body=body;
        this.ans=ans;
        //this.uid=uid;
        this.date=date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    /*public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }*/

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
