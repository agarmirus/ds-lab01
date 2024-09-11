package ds.domain;

public class Person
{
    private Integer id = 0;
    private String name = "";
    private Integer age = 0;
    private String address = "";
    private String work = "";

    public Person() {}
    public Person(
        final Integer id,
        String name,
        final Integer age,
        String address,
        String work
    )
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.work = work;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public Integer getAge() { return age; }
    public String getAddress() { return address; }
    public String getWork() { return work; }

    public void setId(final Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(final Integer age) { this.age = age; }
    public void setAddress(String address) { this.address = address; }
    public void setWork(String work) { this.work = work; }
}
