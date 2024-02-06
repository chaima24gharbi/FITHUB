<?php
namespace App\Controller;

use App\Entity\Consultation;
use App\Repository\ConsultationRepository;
use App\Repository\UserRepository;
use App\Repository\FicheRepository;
use DateTime;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/mobile/consultation")
 */
class ConsultationMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(ConsultationRepository $consultationRepository): Response
    {
        $consultations = $consultationRepository->findAll();

        if ($consultations) {
            return new JsonResponse($consultations, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, UserRepository $userRepository, FicheRepository $ficheRepository): JsonResponse
    {
        $consultation = new Consultation();

        return $this->manage($consultation, $userRepository,  $ficheRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, ConsultationRepository $consultationRepository, UserRepository $userRepository, FicheRepository $ficheRepository): Response
    {
        $consultation = $consultationRepository->find((int)$request->get("id"));

        if (!$consultation) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($consultation, $userRepository, $ficheRepository, $request, true);
    }

    public function manage($consultation, $userRepository, $ficheRepository, $request, $isEdit): JsonResponse
    {   
        $user = $userRepository->find((int)$request->get("utilisateur"));
        if (!$user) {
            return new JsonResponse("user with id " . (int)$request->get("utilisateur") . " does not exist", 203);
        }
        
    
        
        
        $consultation->constructor(
            $user,
            DateTime::createFromFormat("d-m-Y", $request->get("date")),
            DateTime::createFromFormat("d-m-Y", $request->get("heure")),
            $request->get("type"),
            $request->get("nom")
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($consultation);
        $entityManager->flush();

        return new JsonResponse($consultation, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, ConsultationRepository $consultationRepository): JsonResponse
    {
        $consultation = $consultationRepository->find((int)$request->get("id"));

        if (!$consultation) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($consultation);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, ConsultationRepository $consultationRepository): Response
    {
        $consultations = $consultationRepository->findAll();

        foreach ($consultations as $consultation) {
            $entityManager->remove($consultation);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
}
