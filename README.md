# Mục tiêu 
Xây dựng annotation phục vụ cho việc generate code 

# Hiện tại 
Hiện tại team **CPS** đang có một thư viện Starter hỗ trợ cho các member trong team code nhanh hơn không lo đến các vấn đề như load config.

# Vấn đề 
Khi sử dụng thư viện chúng ta bị lệ thuộc vào những nguyên tắc 

# Góp ý 
Em có tìm hiểu cách generate code và có build thử 1 lib để áp dụng cho tình huống nêu trên. 
Hiện tại khi chúng ta muốn tạo một key để lưu redis. Chúng ta cần thực hiện các bước sau 

1. Tạo class A extend RedisKey 
2. Config prefix redis theo đúng như cách Starter đang load ( zalopay.starter.app.redisPrefix)
3. Khi đó class A phải viết các method generate key 

Nếu sử dụng annotation generate, thì Starter lúc đó cung cấp cho client 1 annotation và sẽ thực hiện gen code bên trong nội tại với một template được build sẵn sử dụng cho các trường hợp khác 


