using Microsoft.AspNetCore.Mvc;
using System.Drawing;
using System.Drawing.Imaging;

namespace FtpServer.Controllers
{
    public class HomeController : Controller
    {
        private IWebHostEnvironment _env;

        public HomeController(IWebHostEnvironment _webHostEnvironment)
        {
            _env = _webHostEnvironment;
        }

        public IActionResult Index()
        {
            return View();
        }

        [HttpPost]
        [Route("uploadFile")]
        public IActionResult UploadFile([FromBody] string base64string) 
        {
            byte[] byteBuffer = Convert.FromBase64String(base64string);
            Bitmap bmp = null;

            try{
                using (MemoryStream memoryStream = new MemoryStream(byteBuffer)){
                    memoryStream.Position = 0;
                    Image imgReturn;
                    imgReturn = Image.FromStream(memoryStream);
                    memoryStream.Close();
                    byteBuffer = null;
                    bmp = new Bitmap(imgReturn);
                }
            }
            catch { 
                bmp = null;
            }

            
            string randomFilename = Path.GetRandomFileName() + ".jpeg";
            var dir = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "uploads", randomFilename);
            bmp.Save(dir, ImageFormat.Jpeg);

            return Ok();
        }  

        [HttpPost]
        [Route("test")]
        public IActionResult test([FromBody] string msg)
        {
            return Ok(msg);
        }
    }
}
