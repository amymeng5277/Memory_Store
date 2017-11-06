exception IllegalArgument{
    1:string message;
}

service KeyValueService{
    list<string> multiGet(1:list<string> keys);
    void multiPut(1:list<string> keys,2:list<string> values)
                throws (1:IllegalArgument info);
    string get(1:string key);
    void put(1:string key,2:string value);
}