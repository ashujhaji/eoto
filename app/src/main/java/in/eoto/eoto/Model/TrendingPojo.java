package in.eoto.eoto.Model;

/**
 * Created by Ashu on 20-Mar-18.
 */

public class TrendingPojo {
    String uname, date, category, ques_head, ques_body, uid;
    public TrendingPojo(String uname, String date, String category, String ques_head, String ques_body, String uid){
        this.uname=uname;
        this.date=date;
        this.category=category;
        this.ques_head=ques_head;
        this.ques_body=ques_body;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQues_head() {
        return ques_head;
    }

    public void setQues_head(String ques_head) {
        this.ques_head = ques_head;
    }

    public String getQues_body() {
        return ques_body;
    }

    public void setQues_body(String ques_body) {
        this.ques_body = ques_body;
    }
}

