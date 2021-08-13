package Utils;

public class Session {

    private String id;
    private String value;
    private String storyId;
    private String commentId;
    private String attachmentId;

//    Utils.Session(String responseId, String responseValue){
//        this.id = responseId;
//        this.value = responseValue;
//    }

    public void setId(String id){
        this.id = id;
    }

    public void setValue(String value){
        this.value = value;
    }

    public void setStoryId(String storyId){ this.storyId = storyId; }

    public void setCommentId(String commentId){ this.commentId=commentId; }

    public void setAttachmentId(String attachmentId){ this.attachmentId=attachmentId;}

    public String getId(){
        return id;
    }

    public String getValue(){
        return value;
    }

    public String getStoryId(){ return storyId;}

    public String getCommentId(){ return commentId;}

    public String getAttachmentId(){ return attachmentId;}
}
