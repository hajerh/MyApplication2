using Domain.Entity;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Service;
using System.Web.Mvc;
using System.Net.Http;
using Data;

namespace Pidev.Controllers
{
    public class FeedbackController : Controller
    {
       
        // GET: Feedback
        public ActionResult Index()
        {

            var client = new RestClient("http://localhost:18080/pidev-web/rest/");
            var request = new RestRequest("feedback", Method.GET);
            request.AddHeader("Content-type", "application/json");

            
            IRestResponse<List<feedback>> feedbacks = client.Execute<List<feedback>>(request);
            
            return View(feedbacks.Data);
        }



        
        public ActionResult Create()
        {
            return View();
        }




        [HttpPost]
        public ActionResult Create(HttpPostedFileBase photo)
        {
            try
            {
                var description = Request.Form["description"];


                var top = new feedback();

                top.description = (description);

                var restClient = new RestClient("http://localhost:18080/pidev-web/rest/");
                restClient.AddDefaultHeader("accept", "*/*");
                var request = new RestRequest("feedback", Method.POST);
                request.AddJsonBody(new
                {
                    description=description,

                });
                var response = restClient.Execute(request);
                return RedirectToAction("Index");

            }
            catch (Exception ex)
            {
                ViewBag.Message = "ERROR:" + ex.Message.ToString();
                return RedirectToAction("Index");
            }
        }




        public ActionResult Like(int id)
        {
            using (PidevContext ctx = new PidevContext())
            {
                client.BaseAddress = new Uri("http://localhost:18080/");
                var deleteTask = client.DeleteAsync("pidev-web/rest/feedback/" + id.ToString());
                deleteTask.Wait();
                var result = deleteTask.Result;
                if (result.IsSuccessStatusCode)
                {
                    return RedirectToAction("Index");
                }
            }
            return View();
        }





        public ActionResult Delete(int id)
        {
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri("http://localhost:18080/");
                var deleteTask = client.DeleteAsync("pidev-web/rest/feedback/" + id.ToString());
                deleteTask.Wait();
                var result = deleteTask.Result;
                if (result.IsSuccessStatusCode)
                {
                    return RedirectToAction("Index");
                }
            }
            return View();
        }








    }
    }
