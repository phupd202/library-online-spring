$(document).ready(function() {
    var buttonDelete = $('.btn-danger');
    var buttonRecovery = $('.btn-warning');

    $(buttonDelete).click(function(event) {
        event.preventDefault();
        var clickedButton = $(this);
        var accountId = /*[[${authority.accountId}]]*/ '';
        var action = clickedButton.data('action');

        var formData = {
            'accountId': accountId,
            'action':action
        }

        // Hiển thị xác nhận từ người dùng
        if (confirm("Bạn có chắc muốn xoá không?")) {
            // Thêm token CSRF vào header của yêu cầu
            var tokenCsrf = $("meta[name='_csrf']").attr("content");
            var headerCsrf = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                type: 'POST',
                url: '/library-online/admin/list-user',
                data: formData,
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(headerCsrf, tokenCsrf);
                },
                
                success: function() {
                    console.log('Thành công');
                    clickedButton.removeClass('btn-danger').addClass('btn-warning').text('Xác nhận');
                    console.log(JSON.stringify(formData));
                },
                error: function() {
                    alert('Không thành công, vui lòng kiểm tra lại!!');
                    console.log(JSON.stringify(formData));
                }
            });
        }
    });

    $(buttonRecovery).click(function(event) {
        event.preventDefault();
        var clickedButton = $(this);
        var accountId = /*[[${authority.accountId}]]*/ '';
        var action = clickedButton.data('action');
        
        var formData = {
            'accountId': accountId,
            'action': action
        }

        // Hiển thị xác nhận từ người dùng
        if (confirm("Bạn có chắc muốn khôi phục không?")) {
            // Thêm token CSRF vào header của yêu cầu
            var tokenCsrf = $("meta[name='_csrf']").attr("content");
            var headerCsrf = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                type: 'POST',
                url: '/library-online/admin/list-user',
                data: formData,
                contentType: 'application/json',
                dataType:'json',
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(headerCsrf, tokenCsrf);
                },
                
                success: function() {
                    console.log('Thành công');
                    clickedButton.removeClass('btn-warning').addClass('btn-danger').text('Xoá');
                    console.log(JSON.stringify(formData));
                },
                error: function() {
                    alert('Không thành công, vui lòng kiểm tra lại!!');
                    console.log(JSON.stringify(formData));
                }
            });
        }
    });
});


// //--> Đoạn mã này gửi dữ liệu bàng JSON, do vậy sử dụng mô hình truyền thống với Thymeleaf có thể sẽ không sử dụng được
// // --> Có thể sử dụng AJAX thông quan phương thức .get,.post() của JQuery và truyền dữ liệu thông qua param --> dễ dàng bắt với thymleaf
// // $(document).ready(function() {
// //     var buttonDelete = $(".btn-danger")
// //     var buttonRecovery = $(".btn-warning")

// //     $(".btn danger").click(function(event) {
// //         event.preventDefault();
// //         var clickedButton = $(this)
// //         var accountId = clickedButton.data("account-id")
// //         var action = clickedButton.data("action")

// //         // data
// //         var formData = {
// //             param1: accountId,
// //             param2: action
// //         }

// //         // send request post
// //         $.post('/library-online/admin/list-user', formData, function() {
// //             clickedButton.removeClass('btn-danger').addClass('btn-warning').text('Xác nhận');
// //             console.log("Thành công");
// //             alert("Thành công")
// //         })
// //         .fail(function() {
// //             alert("Không thành công")
// //         })
// //     });
// // });