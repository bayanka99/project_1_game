package Business_Layer;

public  class Resource { // health and mana
	private int max;
	private int dynamic;
	
	public Resource(int dyna,int pool)
	{
		this.dynamic=dyna;
		this.max=pool;
	}
	
	public void updatehealth(int level)
	{
		this.max=this.max+10*level;
		this.dynamic=this.max;
	}
	
	public void warriorbonus(int level)//health
	{
		this.max=this.max+5*level;
		// there is no need to do this because when i reach this it means i have already did regular update this.health=this.maxhealth;
	}
	
	public void Avengers_Shield(int defence)
	{
		this.dynamic=Math.min(this.dynamic+10*defence, this.max);
	}
	public void magebonus(int level)// resource is considered as mana, check mage fields
	{
		this.max=this.max+25*level;
		this.dynamic=Math.min(this.dynamic+this.max/4, this.max);
	}
	
	public void Mage_Ongametick(int level)
	{
		this.dynamic=Math.min(this.max,this.dynamic+level);
	}
	
	public void Reduce(int amount)
	{
		if(this.dynamic<amount)
		{
			this.dynamic=0;
		}
		else
		{
			this.dynamic=this.dynamic-amount;
		}
	}
	
	
	public boolean Depleted()
	{
		return this.dynamic==0;
	}
	
	public int Get_Health_Pool()
	{
		return this.max;
	}
	
	public int Get_Dynamic()
	{
		return this.dynamic;
	}
	
	
	
	
	
	
	

}
