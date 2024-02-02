$(document).ready(function() {
    var buttonDelete = $('.btn-danger');
    var buttonRecovery = $('.btn-warning');

    $(buttonDelete).click(function(event) {
        event.preventDefault();
        var clickedButton = $(this);
        var accountId = clickedButton.attr('id')
        var action = clickedButton.attr('data-action');

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
                url: '/library-online/admin/user/delete-user',
                data: JSON.stringify(formData), // dữ liệu mà server nhận được
                contentType: 'application/json', // loại dữ liệu của yêu cầu
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(headerCsrf, tokenCsrf);
                },
                
                success: function() {
                    console.log('Thành công');
                    clickedButton.removeClass('btn-danger').addClass('btn-warning').text('Khôi phục');
                    console.log(JSON.stringify(formData));
                    // check phần tử nào được nhấn
                    console.log(clickedButton);

                },

                error: function() {
                    alert('Không thành công, vui lòng kiểm tra lại!!');
                    console.log(JSON.stringify(formData));
                    console.log(clickedButton);

                }
            });
        }
    });

    $(buttonRecovery).click(function(event) {
        event.preventDefault();
        var clickedButton = $(this);
        var accountId = clickedButton.attr('id');
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
                url: '/library-online/admin/user/recovery-user',
                data: JSON.stringify(formData), // data gửi cho server(dạng chuỗi JSON)
                // dataType: 'json', kiểu dữ liệu mong muốn nhận lại tử server --> không cần thiết --> do trong th này server không gửi lại dữ liệu cho client
                contentType: 'application/json',
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(headerCsrf, tokenCsrf);
                },
                success: function() {
                    console.log('Thành công');
                    clickedButton.removeClass('btn-warning').addClass('btn-danger').text('Khoá');
                    console.log(JSON.stringify(formData));
                    console.log(clickedButton);

                },
                error: function() {
                    alert('Không thành công, vui lòng kiểm tra lại!!');
                    console.log(JSON.stringify(formData));
                    // check phần tử nào được nhấn
                    console.log(clickedButton);
                }
            });
        }
    });
});