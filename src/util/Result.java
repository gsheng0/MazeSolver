package util;

public class Result<E> {
    private boolean isError = false;
    private E e;
    private String error;
    public Result(E e){
        this.e = e;
    }
    public Result(String error){
        isError = true;
        this.error = error;
    }
    public Result(){
        isError = true;
    }
    public boolean error() { return isError; }
    public E get(){ return e; }
    public String getError() { return error; }
}
