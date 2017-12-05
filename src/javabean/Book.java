package javabean;

public class Book {
    private String title; /*书名*/
    private String author; /*作者*/
    private String price; /*价格*/
    private String pubDate; /*出版日期*/
    private String publisher; /*出版社*/
    private String isbnNumber; /*isbn号*/
    private String pages; /*页数*/
    private String summary; /*简要*/
    private String image; /*图书image的url*/
    private String tag;

    public void setTitle(String title) {this.title = title;}
    public void setAuthor(String author) {this.author = author;}
    public void setPrice(String price) {this.price = price;}
    public void setPubDate(String pubDate) {this.pubDate = pubDate;}
    public void setPublisher(String publisher) {this.publisher = publisher;}
    public void setIsbnNumber(String isbnNumber) {this.isbnNumber = isbnNumber;}
    public void setPages(String pages) {this.pages = pages;}
    public void setSummary(String summary) {this.summary = summary;}
    public void setImage(String image) {this.image = image;}
    public void setTag(String tag) {this.tag = tag;}

    public String getTitle(){return title;}
    public String getAuthor(){return author;}
    public String getPrice() {return price;}
    public String getPubDate() {return pubDate;}
    public String getPublisher(){return publisher;}
    public String getIsbnNumber() {return isbnNumber;}
    public String getPages() {return pages;}
    public String getSummary() {return summary;}
    public String getImage() {return image;}
    public String getTag() {return tag;}
}
