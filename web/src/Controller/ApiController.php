<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use App\Repository\ConsultationRepository;
use App\Repository\FicheRepository;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Consultation;
use App\Entity\Fiche;

class ApiController extends AbstractController
{
    #[Route('/api', name: 'app_api')]
    public function index(): Response
    {
        return $this->render('api/index.html.twig', [
            'controller_name' => 'ApiController',
        ]);
    }

    
    #[Route("/Consultation/{id}", name: "consultation")]
    public function ConsultationId($id, NormalizerInterface $normalizer, ConsultationRepository $repo)
    {
        $consultation = $repo->find($id);
        $consultationNormalises = $normalizer->normalize($consultation, 'json', ['groups' => "consultations"]);
        return new Response(json_encode($consultationNormalises));
    }



    #[Route("/Fiche/{id}", name: "fiche")]
    public function FicheId($id, NormalizerInterface $normalizer, FicheRepository $repo)
    {
        $fiche = $repo->find($id);
        $ficheNormalises = $normalizer->normalize($fiche, 'json', ['groups' => "fiches"]);
        return new Response(json_encode($ficheNormalises));
    }


    #[Route("/addConsultationJSON/new", name: "addConsultationJSON")]
    public function addConsultationJSON(Request $req,   NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $consultation = new Consultation();
        $consultation->setDateConsultation($req->get('dateConsultation'));
        $consultation->setHeureConsultation($req->get('heureConsultation'));
        $consultation->setTypeConsultation($req->get('typeConsultation'));
        $consultation->setNom($req->get('nom'));
        $consultation->setUtilisateur($req->get('utilisateur'));
        $em->persist($consultation);
        $em->flush();

        $jsonContent = $Normalizer->normalize($consultation, 'json', ['groups' => 'consultations']);
        return new Response(json_encode($jsonContent));
    }

    #[Route("/updateConsultationJSON/{id}", name: "updateConsultationJSON")]
    public function updateConsultationJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $consultation = $em->getRepository(Consultation::class)->find($id);
        $consultation->setDateConsultation($req->get('dateConsultation'));
        $consultation->setHeureConsultation($req->get('heureConsultation'));
        $consultation->setTypeConsultation($req->get('typeConsultation'));
        $consultation->setNom($req->get('nom'));
        $consultation->setUtilisateur($req->get('utilisateur'));

        $em->flush();

        $jsonContent = $Normalizer->normalize($consultation, 'json', ['groups' => 'consultations']);
        return new Response("Consultation updated successfully " . json_encode($jsonContent));
    }

    #[Route("/deleteConsultationJSON/{id}", name: "deleteConsultationJSON")]
    public function deleteConsultationJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $consultation = $em->getRepository(Consultation::class)->find($id);
        $em->remove($consultation);
        $em->flush();
        $jsonContent = $Normalizer->normalize($consultation, 'json', ['groups' => 'consultations']);
        return new Response("Consultation deleted successfully " . json_encode($jsonContent));
    }






    #[Route("/addFicheJSON/new", name: "addFicheJSON")]
    public function addFicheJSON(Request $req,   NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $fiche = new Fiche();
        $fiche->setDescriptionFiche($req->get('descriptionFiche'));
        $fiche->setNomFiche($req->get('nomFiche'));
        $fiche->setCategory($req->get('category'));
        $fiche->setConsultation($req->get('consultation'));
        $em->persist($fiche);
        $em->flush();

        $jsonContent = $Normalizer->normalize($fiche, 'json', ['groups' => 'fiches']);
        return new Response(json_encode($jsonContent));
    }

    #[Route("/updateFicheJSON/{id}", name: "updateFicheJSON")]
    public function updateFicheJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $fiche = $em->getRepository(Fiche::class)->find($id);
        $fiche->setDescriptionFiche($req->get('descriptionFiche'));
        $fiche->setNomFiche($req->get('nomFiche'));
        $fiche->setCategory($req->get('category'));
        $fiche->setConsultation($req->get('consultation'));

        $em->flush();

        $jsonContent = $Normalizer->normalize($fiche, 'json', ['groups' => 'fiches']);
        return new Response("Fiche updated successfully " . json_encode($jsonContent));
    }

    #[Route("/deleteFicheJSON/{id}", name: "deleteFicheJSON")]
    public function deleteFicheJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $fiche = $em->getRepository(Fiche::class)->find($id);
        $em->remove($fiche);
        $em->flush();
        $jsonContent = $Normalizer->normalize($fiche, 'json', ['groups' => 'fiches']);
        return new Response("Fiche deleted successfully " . json_encode($jsonContent));
    }


}
