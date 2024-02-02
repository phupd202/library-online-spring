$(document).ready(function () {
    var modifierButton = $(".btn-primary");

    $(modifierButton).click(function (event) {
        event.preventDefault();
        // process
        // var email = modifierButton.parent().parent().children(':nth-child(2)').text();

        // Thêm token CSRF vào header của yêu cầu
        var tokenCsrf = $("meta[name='_csrf']").attr("content");
        var headerCsrf = $("meta[name='_csrf_header']").attr("content");

        // Khi nhấn nút cập nhật thì ajax được gửi
        var updateButton = $(".btn-primary")
        updateButton.click(function (event) {
            event.preventDefault();

            // Lấy dữ liệu khi người dùng nhấn nút cập nhật
            var emailArray = [];
            modifierButton.parent().parent().find('td:nth-child(2)').each(function () {
                emailArray.push($(this).text().trim());
            });

            // get email
            var uniqueEmailArray = Array.from(new Set(emailArray));
            var email = uniqueEmailArray[0];
            
            var phone = $("form input[id = 'phone-id-form']").val();

            var role = $("form select[id = 'role-id-form']").val().trim();

            // prepare data
            var formData = {
                "email": email,
                "phone": phone,
                "role": role
            }
            
            // ajax
            $.ajax({
                url: "/library-online/admin/user/update-user",
                method: "POST",
                data: JSON.stringify(formData),
                contentType: "application/json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(headerCsrf, tokenCsrf);
                },
                success: function () {
                    alert("Cập nhật thành công");
                    $('#editModal .modal-body').html("<h4>Cập nhật thành công!</h4>");

                    
                    // Cập nhật lại nội dung bảng
                    modifierButton.closest("tr").find(":nth-child(3)").text(phone);
                    modifierButton.closest("tr").find(":nth-child(4)").text(role);

                    // In log
                    console.log(phone);
                    console.log(role);
                    console.log("Data gửi đi: ", JSON.stringify(formData));
                },
                error: function () {
                    alert("Cập nhật không thành công");
                    console.log("Data gửi đi: ", JSON.stringify(formData));
                    console.log(phone);
                    console.log(role);
                }
            });
            // end ajax
        });
    });

});