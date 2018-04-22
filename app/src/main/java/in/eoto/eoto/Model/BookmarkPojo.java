package in.eoto.eoto.Model;

/**
 * Created by Ashu on 20-Mar-18.
 */

public class BookmarkPojo {
    String qid, body, category, date, head,uid;
    public BookmarkPojo(String qid, String body, String category, String date, String head, String uid){
        this.qid=qid;
        this.body=body;
        this.category=category;
        this.date=date;
        this.head=head;
        this.uid=uid;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
