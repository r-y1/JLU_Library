package entity;

public class OrderItem {
    @Override
	public String toString() {
		return "OrderItem [id=" + id + ", price=" + price + ", amount=" + amount + ", goodsName=" + goodsName
				+ ", goods=" + goods + ", order=" + order + "]";
	}
	private int id;
    private float price;
    private int amount;
    private String goodsName;
//    改：
    private int goodsId;
    private Goods goods;
    private Order order;// order_id

    public void setName(String name) {
        this.goodsName=name;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsId(int id){this.goodsId = id;}
    public int getgoodsId() {
        return goodsId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Goods getGoods() {
        return goods;
    }
    public void setGoods(Goods goods) {
        this.goods = goods;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public OrderItem() {
        super();
    }
    public OrderItem(float price, int amount, Goods goods, Order order) {
        super();
        this.price = price;
        this.amount = amount;
        this.goods = goods;
        this.order = order;
    }
//    改：
    public OrderItem(float price, int amount,String name,int goodsId, Goods goods, Order order) {
        super();
        this.price = price;
        this.amount = amount;
        this.goodsName = name;
        this.goodsId = goodsId;
        this.goods = goods;
        this.order = order;
    }
}
